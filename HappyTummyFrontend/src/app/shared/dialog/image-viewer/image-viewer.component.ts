import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipesService } from 'src/app/services/recipes.service';
import { RecipeUploadDialogComponent } from '../recipe-upload-dialog/recipe-upload-dialog.component';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss']
})
export class ImageViewerComponent {

  file : File;
  selectedImage: any;

  
  constructor(private dialogRef: MatDialogRef<RecipeUploadDialogComponent>,@Inject(MAT_DIALOG_DATA) public data: {name: string}) {
  }

  close() {
    this.dialogRef.close();
  }
}
