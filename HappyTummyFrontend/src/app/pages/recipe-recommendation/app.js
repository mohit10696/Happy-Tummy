app.filter('filterByIngredients', function() {
  return function(input, ingredient) {
    var filtered = [];
    for (var i = 0; i < input.length; i++) {
      var ingredient = input[i];
      if (ingredients.indexOf(ingredient) > -1) {
        filtered.push(ingredient);
      }
    }
    return filtered;
  }
});

app.filter('filterBySearch',function()
{
  return function(input, search) {
    var filtered = [];
    for (var i = 0; i < input.length; i++) {
      var ingredient = input[i];
      if (ingredient.toLowerCase().indexOf(search.toLowerCase()) > -1) {
        filtered.push(ingredient);
      }
    }
    return filtered;
  }
});
