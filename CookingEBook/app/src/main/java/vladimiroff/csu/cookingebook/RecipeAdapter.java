package vladimiroff.csu.cookingebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    Context context;
    Recipe[] objects;

    public RecipeAdapter(Context context, int resource, Recipe[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.recipe_row, null);

        TextView label = (TextView) row.findViewById(R.id.recipe_text);
        ImageView image = (ImageView) row.findViewById(R.id.recipe_thumb);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);

        label.setText(objects[position].getName());
        image.setImageResource(objects[position].getImage());

        final int pos = position;
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getContext(), TextActivity.class);
                next.putExtra("recipe", objects[pos].getRecipe());
                getContext().startActivity(next);
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getContext(), ImageActivity.class);
                next.putExtra("image", objects[pos].getImage());
                getContext().startActivity(next);
            }
        });

        return row;
    }

}
