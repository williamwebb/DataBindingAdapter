package io.jug6ernaut.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by williamwebb on 2/3/16.
 */
public class LayoutManagers {

  public static LayoutManagerFactory linear() {
    return new LayoutManagerFactory() {
      public RecyclerView.LayoutManager build(Context context){
        return new  LinearLayoutManager(context);
      }
    };
  }

  @BindingAdapter({"layoutManager"})
  public static void setLayoutManaer(RecyclerView view, LayoutManagerFactory factory) {
    view.setLayoutManager(factory.build(view.getContext()));
  }

  public static interface LayoutManagerFactory {
    public RecyclerView.LayoutManager build(Context context);
  }
}
