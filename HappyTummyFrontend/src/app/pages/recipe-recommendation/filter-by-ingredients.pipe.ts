import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByIngredients'
})
export class FilterByIngredientsPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
