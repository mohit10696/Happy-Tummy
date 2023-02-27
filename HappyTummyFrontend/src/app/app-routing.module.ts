import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('./pages/dashboard/dashboard.module').then(m => m.DashboardModule)
  },
  {
    path: 'recipe-recommendation',
    loadChildren: () => import('./pages/recipe-recommendation/recipe-recommendation.module').then(m => m.RecipeDetailModule)
  },
  {
    path: 'recipe',
    loadChildren: () => import('./pages/recipe-detail/recipe-detail.module').then(m => m.RecipeDetailModule)
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
