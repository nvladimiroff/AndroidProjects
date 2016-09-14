package vladimiroff.csu.cookingebook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    Recipe recipeArray[] = {
            new Recipe("Broccoli Cheddar Bites", R.mipmap.broccoli,
                "    1 large bunch of broccoli florets\n" +
                        "    2 eggs, lightly beaten\n" +
                        "    1/2 cup, packed, torn fresh bread (no crust)\n" +
                        "    1/4 cup grated onion\n" +
                        "    1/4 cup mayonnaise\n" +
                        "    1 cup (4 ounces, 114 g), packed, grated sharp cheddar cheese\n" +
                        "    1 1/2 teaspoons lemon zest\n" +
                        "    1 1/2 teaspoons kosher salt\n" +
                        "    1/4 teaspoon freshly ground black pepper\n"),
            new Recipe("Pasta Pomodoro with Shrimp", R.mipmap.shrimp,
                "    1/2 pound angel hair pasta\n" +
                        "    3/4 pound raw, peeled, deveined shrimp (medium sized)\n" +
                        "    4 medium ripe tomatoes\n" +
                        "    3 cloves garlic, minced\n" +
                        "    1/3 cup torn fresh basil leaves\n" +
                        "    3 Tbsp olive oil (divided, 1 Tbsp and 2 Tbsp)\n" +
                        "    Salt\n" +
                        "    Pepper\n"),
            new Recipe("Avocado Chicken Salad", R.mipmap.avocado,
                "    1 cup finely chopped cooked chicken\n" +
                        "    1 ripe avocado, seeded and peeled (see How to Cut and Peel an Avocado)\n" +
                        "    1 apple, peeled, cored, and finely chopped\n" +
                        "    1/4 cup finely chopped celery\n" +
                        "    1/4 cup finely chopped red onion\n" +
                        "    2 Tbsp finely chopped cilantro or parsley\n" +
                        "    2 teaspoons lime juice or a tablespoon of lemon juice\n" +
                        "    1/2 teaspoon kosher salt\n" +
                        "    Pinch of freshly ground black pepper\n" +
                        "    2 pounds (900 g) bone-in, skin-on chicken thighs, trimmed of excess fat\n" +
                        "    1/2 teaspoon kosher salt\n" +
                        "    2 cups of cherry tomatoes (a heaping basket or 25 to 30 cherry tomatoes)\n" +
                        "    6 whole cloves garlic, peeled\n" +
                        "    1 1/2 Tbsp balsamic vinegar\n" +
                        "    1 1/2 teaspoons olive oil\n" +
                        "    2 sprigs fresh rosemary (or 1/2 teaspoon dry rosemary)\n"),
            new Recipe("Baked Chicken with Cherry Tomatoes and Garlic", R.mipmap.chicken,
                "    2 Tbsp unsalted butter\n" +
                        "    1 cup of finely chopped onion\n" +
                        "    1 celery rib, finely chopped\n" +
                        "    1 carrot, finely chopped\n" +
                        "    1/2 cup of finely chopped green onion, including the green onion greens\n" +
                        "    3 cloves garlic, minced (about 1 Tbsp)\n" +
                        "    2 teaspoon salt (use 1 1/2 teaspoons if using Italian sausage)\n" +
                        "    1/2 teaspoon freshly ground black pepper\n" +
                        "    2 teaspoons Worcestershire sauce\n" +
                        "    2/3 cup ketchup, divided 1/3 and 1/3\n" +
                        "    1 1/2 pounds of ground beef (chuck)\n" +
                        "    3/4 pound of spicy ground pork sausage or Italian sausage (a mix of sweet and hot if you are using links)\n" +
                        "    1 cup fresh bread crumbs (take a couple slices of fresh bread and pulse in a blender until you have crumbs)\n" +
                        "    2 large eggs, beaten slightly\n" +
                        "    1/3 cup minced fresh parsley leaves\n"),
            new Recipe("Apple Crostada", R.mipmap.pie,
                "    2 Tbsp butter\n" +
                        "    2 cups sliced onion\n" +
                        "    1 1/2 cups sliced celery (about 2 to 3 ribs)\n" +
                        "    1/2 cup sliced carrot (1 small carrot)\n" +
                        "    1 1/2 Tbsp minced garlic\n" +
                        "    2 yukon gold potatoes, peeled and thinly sliced, about 1 1/2 to 2 cups\n" +
                        "    6 cups chicken stock\n" +
                        "    6 cups of roughly chopped cauliflower florets\n" +
                        "    2 bay leaves\n" +
                        "    1 1/2 teaspoons fresh thyme leaves, or 1 teaspoon dried thyme\n" +
                        "    1 teaspoon kosher salt\n" +
                        "    1/8 teaspoon freshly ground black pepper\n" +
                        "    1 1/2 cups grated sharp cheddar cheese (about 6 ounces)\n" +
                        "    1 teaspoon Worcestershire sauce\n"
            )};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeAdapter recipeAdapter = new RecipeAdapter(this, R.layout.recipe_row, recipeArray);

        setListAdapter(recipeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
