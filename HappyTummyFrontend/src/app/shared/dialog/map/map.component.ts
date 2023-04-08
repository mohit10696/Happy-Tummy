import { AgmMap, LatLngBounds } from '@agm/core';
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { RecipeUploadDialogComponent } from '../recipe-upload-dialog/recipe-upload-dialog.component';
declare var google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  location = {
    latitude: 44.6476,
    longitude: 63.5728
  };
  maplocation = {
    latitude: 44.6476,
    longitude: 63.5728
  };
  zoom = 12;
  stores = [];
  radius = 500;
  @ViewChild('AgmMap') agmMap: AgmMap;
  constructor(private cdr: ChangeDetectorRef,public dialogRef: MatDialogRef<RecipeUploadDialogComponent> ) { }

  ngOnInit(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.location = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude
        }
        this.maplocation = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude
        }
      });
    }


  }


  mapReady($event: any) {
    this.getNearbyStores(this.radius);
  }

  getNearbyStores(radius) {
    const service = new google.maps.places.PlacesService(document.createElement('div'));
    service.nearbySearch({
      location: new google.maps.LatLng(this.location.latitude, this.location.longitude),
      radius: radius,
      keyword: 'grocery supermarket'
    }, (rowResults, status) => {
      if (status === google.maps.places.PlacesServiceStatus.OK) {
        console.log(rowResults)
        const results = rowResults.map((result) => ({
          lat: result.geometry.location.lat(),
          lng: result.geometry.location.lng(),
          name: result.name,
          address: result.vicinity,
          rating: result.rating,
          icon: result.icon,
          isOpen: result.opening_hours?.isOpen(),
          place_id: result.place_id,
          geometry: result.geometry,
          selected : false
        }));
        this.stores.push(...results.filter((result) => {
          return this.stores.findIndex((store) => store.place_id === result.place_id) === -1;
        }));
        this.fitbounds();
      }
      if (this.stores.length < 10) {
        this.radius = this.radius + 100;
        this.getNearbyStores(this.radius);
      }
    });
  }

  fitbounds() {
    const bounds: LatLngBounds = new google.maps.LatLngBounds();
    this.stores.forEach((store) => {
      bounds.extend(new google.maps.LatLng(store.lat, store.lng));
    });
    this.agmMap.triggerResize().then(() => {
      this.agmMap.fitBounds = bounds;
    }
    );
    this.cdr.detectChanges();
  }

  navigateTo(store) {
    window.open(`https://www.google.com/maps/dir/?api=1&destination=${store.geometry.location.lat()},${store.geometry.location.lng()}`);
  }

  markerClick($event, store){
    console.log(store);
  }

  focusTo(item){
    this.maplocation = {
      latitude: item.lat,
      longitude: item.lng
    }
    this.zoom = 15;
    this.stores.forEach((store) => {
      store.selected = false;
    });
    item.selected = true;
    this.cdr.detectChanges();
  }

}
