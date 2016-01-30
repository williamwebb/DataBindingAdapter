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
    this.data.addAll(data);
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
  @BindingAdapter({"databinding:binding_data", "databinding:binding_layout", "databinding:binding_variable"})
  public static void setAdapter(RecyclerView view, List binding_data, int layoutId, String bindingVariableId) {
    DataBindingAdapter adapter = DataBindingAdapter.createBindingAdapter(view.getContext(),layoutId,bindingVariableId);
    adapter.setData(binding_data);
    view.setAdapter(adapter);
    view.getAdapter().notifyDataSetChanged();
  }

  private static DataBindingAdapter createBindingAdapter(Context context, int bindingLayout, String bindingVariable) {
    try {
      Class BR_CLASS = Class.forName(context.getPackageName() + ".BR");
      Field BR = BR_CLASS.getField(bindingVariable);

      int bindingVariableId = BR.getInt(null);

      return new DataBindingAdapter(bindingLayout, bindingVariableId);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

//	Lint test to verify variableId is a variableId, possibly that it exist in layoutId
//	public class EnumDetector extends Detector implements JavaScanner {
//		…
//	}
}