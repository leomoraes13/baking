package ca.leomoraes.bakingapp.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Ingredient;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{

    private List<Ingredient> mEntries;
    private Context mContext;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mEntries = ingredients;
    }

    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_ingredients_layout, viewGroup, false);
        return new IngredientsAdapter.IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {
        Ingredient ingredient = mEntries.get(position);

        holder.title.setText(ingredient.getIngredient());
        holder.subtitle.setText(ingredient.getQuantity() + " (" + ingredient.getMeasure() + ")");
    }

    @Override
    public int getItemCount() {
        if (mEntries == null) {
            return 0;
        }
        return mEntries.size();
    }

    /**
     * When data changes, this method updates the list of ingredients
     * and notifies the adapter to use the new values on it
     */
/*    public void setIngredients(List<Ingredient> ingredients) {
        mEntries = ingredients;
        notifyDataSetChanged();
    }*/

    public Ingredient getItemAtPosition(int position){
        return mEntries.get(position);

    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_ingredients_title) TextView title;
        @BindView(R.id.item_ingredients_subtitle) TextView subtitle;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
