import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import Map from 'ol/Map';
import View from 'ol/View';
import OSM from 'ol/source/OSM';
import {OrderMapService} from "./order-map.service";
import Style from 'ol/style/Style';
import {Icon} from 'ol/style';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';
import {Tile as TileLayer, Vector as VectorLayer} from 'ol/layer';
import {Overlay} from 'ol';

@Component({
  selector: 'app-order-map',
  templateUrl: './order-map.component.html',
  styleUrls: ['./order-map.component.scss']
})
export class OrderMapComponent implements OnInit {
  map: Map = new Map({});
  popupOverlay: Overlay = new Overlay({});
  @ViewChild('popup') popup: ElementRef = new ElementRef<any>({});

  constructor(private orderMapService: OrderMapService) {
  }

  ngOnInit(): void {
    this.orderMapService.getAllActiveOrders().subscribe(res => {
      console.log(res);
      const features: Feature<Point>[] = [];
      res.forEach(order => {
        if (order.user.address) {
          const iconFeature = new Feature({
            geometry: new Point([order.user.address.lon, order.user.address.lat]),
            //order:
          });
          const iconStyle = new Style({
            image: new Icon({
              color: 'rgba(69, 116, 255, .5)',
              src: 'assets/img/marker.svg',
              scale: 0.2
            }),
          });

          iconFeature.setStyle(iconStyle);
          features.push(iconFeature);
        }
      })


      const vectorSource = new VectorSource({
        features: features,
      });

      const vectorLayer = new VectorLayer({
        source: vectorSource,
      });

      const rasterLayer = new TileLayer({
        source: new OSM(),
      });

      this.map = new Map({
        view: new View({
          projection: 'EPSG:4326',
          center: [24.966761, 45.943161],
          zoom: 8,
        }),
        layers: [
          rasterLayer, vectorLayer
        ],
        target: 'ol-map'
      });
    });

  }

}
