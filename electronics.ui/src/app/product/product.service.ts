import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable, of} from 'rxjs';
import {Category} from './category.model';
import {AddProductRequest} from './add-product/add-product-request.model';
import {ResponseMessage} from '../core/model/response-message';
import {ProductItem} from './product-list/product-item.model';
import {ProductDetails} from './product-details/product-details.model';
import {ProductReviewRequest} from './product-reviews/instrument-review-request.model';
import {ShoppingCartResponse} from "../shopping-cart/shopping-cart-response";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly categoriesUrl = environment.apiUrl + '/product/categories';
  private readonly addProductUrl = environment.apiUrl + '/product/add';
  private readonly addAllProductsUrl = environment.apiUrl + '/product/addAll';
  private readonly baseProductUrl = environment.apiUrl + '/product';
  private readonly productReviewUrl = '/review';

  constructor(private http: HttpClient) {
  }


  addProduct(request: AddProductRequest): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.addProductUrl, request);
  }

  addAllProduct(): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.addAllProductsUrl, {});
  }

  getAllProductsFromShoppingCart(ids?: number[]): Observable<ShoppingCartResponse> {
    if (ids !== undefined && ids.length > 0) {
      return this.http.get<ShoppingCartResponse>(this.baseProductUrl + '/shopping-cart?ids=' + ids.join(','));
    }
    return of();
  }

  getAllProducts(ids?: number[]): Observable<Array<ProductItem>> {
    return this.http.get<Array<ProductItem>>(this.baseProductUrl);
  }



  getProductDetails(instrumentId: number): Observable<ProductDetails> {
    return this.http.get<ProductDetails>(this.baseProductUrl + '/' + instrumentId);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesUrl);
  }

  addReviewForProduct(instrumentId: number, review: ProductReviewRequest): Observable<ProductDetails> {
    return this.http.post<ProductDetails>(this.baseProductUrl + '/' + instrumentId + this.productReviewUrl, review);
  }

}
