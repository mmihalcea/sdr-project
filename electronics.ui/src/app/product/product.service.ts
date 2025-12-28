import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Category} from './category.model';
import {AddProductRequest} from './add-product/add-product-request.model';
import {ResponseMessage} from '../core/model/response-message';
import {ProductItem} from './instrument-list/product-item.model';
import {ProductDetails} from './product-details/product-details.model';
import {ProductReviewRequest} from './instrument-reviews/instrument-review-request.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly categoriesUrl = environment.apiUrl + '/product/categories';
  private readonly addInstrumentUrl = environment.apiUrl + '/product/add';
  private readonly addAllInstrumentUrl = environment.apiUrl + '/product/addAll';
  private readonly baseInstrumentUrl = environment.apiUrl + '/product';
  private readonly instrumentReviewUrl = '/review';

  constructor(private http: HttpClient) {
  }


  addProduct(request: AddProductRequest): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.addInstrumentUrl, request);
  }

  addAllProduct(): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.addAllInstrumentUrl, {});
  }

  getAllProducts(ids?: number[]): Observable<Array<ProductItem>> {
    if (ids !== undefined && ids.length > 0) {
      return this.http.get<Array<ProductItem>>(this.baseInstrumentUrl + '?ids=' + ids.join(','));
    }
    return this.http.get<Array<ProductItem>>(this.baseInstrumentUrl);
  }

  getProductDetails(instrumentId: number): Observable<ProductDetails> {
    return this.http.get<ProductDetails>(this.baseInstrumentUrl + '/' + instrumentId);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesUrl);
  }

  addReviewForProduct(instrumentId: number, review: ProductReviewRequest): Observable<ProductDetails> {
    return this.http.post<ProductDetails>(this.baseInstrumentUrl + '/' + instrumentId + this.instrumentReviewUrl, review);
  }

}
