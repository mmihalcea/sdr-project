package edu.sdr.electronics.seed;

import edu.sdr.electronics.domain.Product;
import edu.sdr.electronics.domain.ProductPhoto;
import edu.sdr.electronics.repository.ProductPhotoRepository;
import edu.sdr.electronics.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.boot.CommandLineRunner;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PhotoSeeder implements CommandLineRunner {

    @Value("${seed.photos:false}")
    private boolean enabled;

    private final ProductRepository productRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final ResourceLoader resourceLoader;

    // category_id → list of image files in resources/seed-images/
    private static final Map<Long, List<String>> CATEGORY_IMAGES = Map.ofEntries(
            Map.entry(1L, List.of("1.jpg", "1_2.jpg", "1_3.jpg")),
            Map.entry(2L, List.of("2.jpg", "2_2.jpg", "2_3.jpg")),
            Map.entry(3L, List.of("3.jpg", "3_2.jpg", "3_3.jpg")),
            Map.entry(4L, List.of("4.jpg")),
            Map.entry(5L, List.of("5.jpg")),
            Map.entry(6L, List.of("6.jpg")),
            Map.entry(7L, List.of("7.jpg")),
            Map.entry(8L, List.of("8.jpg", "8_2.jpg")),
            Map.entry(9L, List.of("9.jpg")),
            Map.entry(10L, List.of("10.jpg")),
            Map.entry(11L, List.of("11.jpg")),
            Map.entry(12L, List.of("12.jpg")),
            Map.entry(13L, List.of("13.jpg")),
            Map.entry(14L, List.of("14.jpg")),
            Map.entry(15L, List.of("15.jpg")),
            Map.entry(16L, List.of("16.jpg")),
            Map.entry(17L, List.of("17.jpg")),
            Map.entry(18L, List.of("18.jpg")),
            Map.entry(19L, List.of("19.jpg")),
            Map.entry(20L, List.of("20.jpg")),
            Map.entry(21L, List.of("21.jpg")),
            Map.entry(22L, List.of("22.jpg")),
            Map.entry(23L, List.of("23.jpg"))
    );

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            System.out.println("PhotoSeeder disabled. Set seed.photos=true to enable.");
            return;
        }

        List<Product> products = productRepository.findAll();
        List<Product> noPhotos = productPhotoRepository.findProductsWithoutPhotos();

        if (noPhotos.isEmpty()) {
            System.out.println("All products already have photos.");
            return;
        }

        List<ProductPhoto> photoInserts = new ArrayList<>();
        Map<String, byte[]> cache = new HashMap<>();

        for (Product product : noPhotos) {
            Long catId = product.getCategory().getId();
            List<String> files = CATEGORY_IMAGES.getOrDefault(catId, List.of());

            for (String file : files) {
                byte[] bytes = cache.computeIfAbsent(file,
                        f -> readBytes("seed-images/" + f));

                ProductPhoto pp = new ProductPhoto();
                pp.setProduct(product);
                pp.setPhoto(bytes);
                photoInserts.add(pp);
            }
        }

        productPhotoRepository.saveAll(photoInserts);
        System.out.println("Inserted " + photoInserts.size() + " photos.");
    }

    private byte[] readBytes(String path) {
        try {
            Resource res = resourceLoader.getResource("classpath:" + path);
            try (InputStream in = res.getInputStream()) {
                return in.readAllBytes();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read: " + path, e);
        }
    }
}