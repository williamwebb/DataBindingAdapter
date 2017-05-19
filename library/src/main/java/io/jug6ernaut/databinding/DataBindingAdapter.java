package io.jug6ernaut.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.AnyRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by williamwebb on 11/15/15.
 * <p>
 * RecycleView Adapter class for use with DataBinding.
 */
public class DataBindingAdapter<DataModel, ViewBinder extends ViewDataBinding> extends RecyclerView.Adapter<DataBindingAdapter.DataBindingViewHolder<DataModel, ViewBinder>> {

  private final List<DataModel>                data = new ArrayList<>();
  private final int                            layoutId;
  private final int                            variableId;

  /**
   * Constructor.
   *
   * @param data       data to populate the Adapter with
   * @param layoutId   layout used by the adapter
   * @param variableId variable id used to set DataBinding. Ex: BR.data
   */
  public DataBindingAdapter(@NonNull List<DataModel> data, @LayoutRes int layoutId, @AnyRes int variableId) {
    this.data.addAll(data);
    this.layoutId = layoutId;
    this.variableId = variableId;
  }

  /**
   * Constructor.
   *
   * @param layoutId   layout used by the adapter
   * @param variableId variable id used to set DataBinding. Ex: BR.data
   */
  @SuppressWarnings("unchecked")
  public DataBindingAdapter(@LayoutRes int layoutId, @AnyRes int variableId) {
    this((List<DataModel>) Collections.emptyList(),layoutId,variableId);
  }

  @Override
  public DataBindingViewHolder<DataModel, ViewBinder> onCreateViewHolder(ViewGroup parent, int viewType) {
    return DataBindingViewHolder.from(parent, layoutId, variableId);
  }

  public void setData(List<DataModel> data) {
    this.data.clear();

    if(data != null) this.data.addAll(data);
  }

  @Override
  public void onBindViewHolder(DataBindingViewHolder<DataModel, ViewBinder> holder, int position) {
    holder.bind(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  static class DataBindingViewHolder<DataModel, ViewBinder extends ViewDataBinding> extends ViewHolder {

    private final ViewBinder dataBinding;
    private final int        variableId;

    static <DataModel, ViewBinder extends ViewDataBinding> DataBindingViewHolder<DataModel, ViewBinder> from(ViewGroup parent, int layoutId, int variableId) {
      View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
      return new DataBindingViewHolder<>(v, variableId);
    }

    DataBindingViewHolder(View itemView, int variableId) {
      super(itemView);
      this.variableId = variableId;

      dataBinding = DataBindingUtil.bind(itemView);
    }

    void bind(DataModel data) {
      dataBinding.setVariable(variableId, data);
      dataBinding.executePendingBindings();
    }
  }

  @SuppressWarnings({"unchecked","unused"})
  @BindingAdapter({"binding_data", "binding_layout", "binding_variable"})
  public static void setAdapter(RecyclerView view, List binding_data, int layoutId, String bindingVariableId) {
    if(binding_data == null || binding_data.isEmpty()) return;

    DataBindingAdapter adapter;
    if(view.getAdapter() == null || (!(view.getAdapter() instanceof DataBindingAdapter))) {
      try {
        adapter = DataBindingAdapter.createBindingAdapter("BR", view.getContext(), layoutId, bindingVariableId);
      } catch (BindingException e) {
          throw new RuntimeException("Failed to load DataBinding BR class. If you are using Proguard add proguard rule \"-keep class **.BR {*;}\"",e);
      }
    } else {
      adapter = (DataBindingAdapter) view.getAdapter();
    }

    adapter.setData(binding_data);
    view.setAdapter(adapter);
    view.getAdapter().notifyDataSetChanged();
  }

  private static DataBindingAdapter createBindingAdapter(String className, Context context, int bindingLayout, String bindingVariable) throws BindingException {
    try {
      String full_path = context.getPackageName() + "." + className;

      Class BR_CLASS = Class.forName(full_path);
      Field BR = BR_CLASS.getField(bindingVariable);

      int bindingVariableId = BR.getInt(null);

      return new DataBindingAdapter(bindingLayout, bindingVariableId);
    } catch (ClassNotFoundException e) {
      throw new BindingException(e);
    } catch (IllegalAccessException e) {
      throw new BindingException(e);
    } catch (NoSuchFieldException e) {
      throw new BindingException(e);
    }
  }

  private static class BindingException extends Exception {
    public BindingException(Exception e) {
      super(e);
    }
  }

//	Lint test to verify variableId is a variableId, possibly that it exist in layoutId
//	public class EnumDetector extends Detector implements JavaScanner {
//		…
//	}
}