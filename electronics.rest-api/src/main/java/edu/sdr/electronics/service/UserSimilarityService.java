package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.ProductReview;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.domain.UserSimilarityScore;
import edu.sdr.electronics.repository.ProductReviewRepository;
import edu.sdr.electronics.repository.StoreUserRepository;
import edu.sdr.electronics.repository.UserSimilarityScoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserSimilarityService {

    private final StoreUserRepository storeUserRepository;
    private final ProductReviewRepository productReviewRepository;
    private final UserSimilarityScoreRepository userSimilarityScoreRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateAndStoreSimilarities() {
        userSimilarityScoreRepository.deleteAll();

        List<StoreUser> allUsers = storeUserRepository.findAll();
        Map<Long, List<ProductReview>> allReviews = productReviewRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(r -> r.getStoreUser().getId()));

        List<UserSimilarityScore> similarityScores = new ArrayList<>();

        for (int i = 0; i < allUsers.size(); i++) {
            for (int j = i + 1; j < allUsers.size(); j++) {
                StoreUser user1 = allUsers.get(i);
                StoreUser user2 = allUsers.get(j);

                List<ProductReview> reviews1 = allReviews.getOrDefault(user1.getId(), new ArrayList<>());
                List<ProductReview> reviews2 = allReviews.getOrDefault(user2.getId(), new ArrayList<>());

                double similarity = calculatePearsonCorrelation(reviews1, reviews2);

                if (user1.getAddress() != null && user2.getAddress() != null &&
                    user1.getAddress().getCounty() != null && user2.getAddress().getCounty() != null &&
                    user1.getAddress().getCounty().equals(user2.getAddress().getCounty())) {
                    if(similarity == 0){
                        similarity = 0.3;
                    }else{
                        similarity *= 1.2;
                    }

                }

                if (user1.getOperatingSystem() != null && user1.getOperatingSystem().equals(user2.getOperatingSystem())) {
                    if(similarity == 0){
                        similarity = 0.1;
                    }else{
                        similarity *= 1.1;
                    }
                }

                if (user1.getBrowser() != null && user1.getBrowser().equals(user2.getBrowser())) {
                    if(similarity == 0){
                        similarity = 0.1;
                    }else{
                        similarity *= 1.1;
                    }
                }

                double finalScore = Math.min(similarity, 1.0);

                if (finalScore > 0) {
                    similarityScores.add(new UserSimilarityScore(user1, user2, finalScore));
                    similarityScores.add(new UserSimilarityScore(user2, user1, finalScore));
                }
            }
        }

        userSimilarityScoreRepository.saveAll(similarityScores);
        log.info("User similarity scores updated successfully. Found {} scores.", similarityScores.size());
    }

    private double calculatePearsonCorrelation(List<ProductReview> reviews1, List<ProductReview> reviews2) {
        Map<Long, Integer> ratings1 = reviews1.stream()
                .collect(Collectors.toMap(r -> r.getProduct().getId(), ProductReview::getRating, (oldValue, newValue) -> newValue));
        Map<Long, Integer> ratings2 = reviews2.stream()
                .collect(Collectors.toMap(r -> r.getProduct().getId(), ProductReview::getRating, (oldValue, newValue) -> newValue));

        List<Long> commonProducts = new ArrayList<>(ratings1.keySet());
        commonProducts.retainAll(ratings2.keySet());

        if (commonProducts.isEmpty()) {
            return 0;
        }

        double sum1 = 0, sum2 = 0, sum1Sq = 0, sum2Sq = 0, pSum = 0;
        int n = commonProducts.size();

        for (Long productId : commonProducts) {
            int rating1 = ratings1.get(productId);
            int rating2 = ratings2.get(productId);

            sum1 += rating1;
            sum2 += rating2;
            sum1Sq += Math.pow(rating1, 2);
            sum2Sq += Math.pow(rating2, 2);
            pSum += rating1 * rating2;
        }

        double num = pSum - (sum1 * sum2 / n);
        double den = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / n) * (sum2Sq - Math.pow(sum2, 2) / n));

        if (den == 0) {
            return 0;
        }

        return num / den;
    }
}
