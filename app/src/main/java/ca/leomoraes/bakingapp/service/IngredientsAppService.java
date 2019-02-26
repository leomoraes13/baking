package ca.leomoraes.bakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Ingredient;

/**
 * This is the service that provides the factory to be bound to the collection service.
 */
public class IngredientsAppService extends RemoteViewsService {
    private List<Ingredient> list;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private int mAppWidgetId;

        public StackRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            list = new ArrayList<>();
            list.add(new Ingredient(2.0, "CUP", "double gloucester cheese"));
            list.add(new Ingredient(1.0, "UNITS", "bread crumbs"));
            list.add(new Ingredient(1.0, "TBSPN", "confectioners sugar"));
            list.add(new Ingredient(1.5, "CUP", "chocolate pudding"));
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            final int itemId = R.layout.widget_item;
            Ingredient ingredient = list.get(position);

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), itemId);
            rv.setTextViewText(R.id.widget_item, ingredient.getIngredient());

            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}