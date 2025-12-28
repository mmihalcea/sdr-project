package edu.sdr.electronics.controller;

import edu.sdr.electronics.domain.Category;
import edu.sdr.electronics.dto.request.AddProductRequest;
import edu.sdr.electronics.dto.request.ProductReviewRequest;
import edu.sdr.electronics.dto.response.ProductDetails;
import edu.sdr.electronics.dto.response.ProductItem;
import edu.sdr.electronics.dto.response.ResponseMessage;
import edu.sdr.electronics.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addProduct(@RequestBody AddProductRequest addProductRequest) {
        this.productService.addProduct(addProductRequest);
        return new ResponseEntity<>(new ResponseMessage("Produs adaugat"), HttpStatus.OK);
    }

    @PostMapping("/addAll")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> importAllProduct() {
        this.productService.importAllProducts();
        return new ResponseEntity<>(new ResponseMessage("Produse adaugate!"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> updateProduct(@PathVariable Long id, @RequestBody AddProductRequest addProductRequest) {
        this.productService.addProduct(addProductRequest);
        return new ResponseEntity<>(new ResponseMessage("Produs adaugat"), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductItem>> listProducts(@RequestParam(required = false) List<Long> ids) {
        return new ResponseEntity<>(productService.listAllProducts(ids), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductDetails(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ProductDetails> reviewProducts(@PathVariable Long id, @RequestBody ProductReviewRequest productReviewRequest) {
        return new ResponseEntity<>(productService.reviewProduct(id, productReviewRequest), HttpStatus.OK);
    }
}
