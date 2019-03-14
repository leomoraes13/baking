package ca.leomoraes.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.ui.IngredientsActivity;
import ca.leomoraes.bakingapp.ui.RecipeActivity;
import ca.leomoraes.bakingapp.ui.RecipeItemActivity;

public class IngredientsAppWidget extends AppWidgetProvider {

    public static Recipe mRecipeSelected;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);

        views.setTextViewText(R.id.widget_title, mRecipeSelected.getName());

        Intent intent = new Intent(context, IngredientsActivity.class);
        intent.putExtra(RecipeItemActivity.EXTRA_RECIPE, mRecipeSelected);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                5, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_layout_all, pendingIntent);
        setRemoteAdapter(context, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, IngredientsAppService.class));
    }
}

