import { AfterViewInit, Component } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { LoaderService } from './services/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements AfterViewInit {
  title = 'HappyTummyFrontend';
  constructor(
    private loaderService: LoaderService,
    private spinner: NgxSpinnerService
  ) {}

  ngAfterViewInit() {
    this.loaderService.httpProgress().subscribe((status: boolean) => {
      status ? this.spinner.show() : this.spinner.hide();
    });
  }
}
