package com.kathline.multitype;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class ViewTypeCreator<T, VH extends ViewHolder> {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * @param inflater A layoutInflater to inflate a view resource.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    protected abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *               item at the given position in the data set.
     * @param position   The data ready to bind to the holder.
     * @param data   The data ready to bind to the holder.
     */
    protected abstract void onBindViewHolder(VH holder, int position, T data);

    /**
     * Called by MultiTypeAdapter to display the data with its view holder. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the given item.
     * <p>
     * If you need the position of an item later on (e.g. in a click listener), use
     * {@link ViewHolder#getAdapterPosition()} which will have the updated adapter position.
     * <p>
     * Partial bind vs full bind:
     * <p>
     * The payloads parameter is a merge list from {@link MultiTypeAdapter#notifyItemChanged(int,
     * Object)} {@link MultiTypeAdapter#notifyItemRangeChanged(int, int, Object)}.
     * If the payloads list is not empty, the ViewHolder is currently bound to old data and
     * ItemViewBinder may run an efficient partial update using the payload info.
     * If the payload is empty, ItemViewBinder must run a full bind.
     * ItemViewBinder should not assume that the payload passed in notify methods will be
     * received by onBindViewHolder().  For example when the view is not attached to the screen,
     * the payload in notifyItemChange() will be simply dropped.
     *
     * This implementation calls the {@code onBindViewHolder(ViewHolder, Object)} by default.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * given item in the items data set.
     * @param position The item within the MultiTypeAdapter's items data set.
     * @param data The item within the MultiTypeAdapter's items data set.
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full
     * update.
     */
    protected void onBindViewHolder(@NonNull VH holder, int position, T data,  @NonNull List<Object> payloads) {
        onBindViewHolder(holder, position, data);
    }

    /**
     * If the Data bind more than one viewTypeCreator, we use this function to mark the viewTypeCreator which you expect want.
     * Attention that you should not do too much work here, just make simple to mark diff for the viewTypeCreator and data.
     * For example:
     * <p>
     * Data bean Person bind ManViewTypeCreator and FemaleViewTypeCreator
     * <p>
     * <code>
     * data class Person(val sex: Int)
     * <p>
     * class ManViewTypeCreator : ViewTypeCreator<Person, ManHolder>(){
     * override fun match(data: Person) = data.sex == Sex.MAN
     * }
     * <p>
     * class FemaleViewTypeCreator : ViewTypeCreator<Person, FemaleHolder>(){
     * override fun match(data: Person) = data.sex == Sex.FEMALE
     * }
     * </code>
     *
     * @param data The data ready to bind ui of current viewTypeCreator.
     */
    public abstract boolean match(T data);

    /**
     * Return the stable ID for the item at <code>position</code>. If {@link RecyclerView.Adapter#hasStableIds()}
     * would return false this method should return {@link RecyclerView#NO_ID}. The default implementation
     * of this method returns {@link RecyclerView#NO_ID}.
     *
     * @param position Adapter position to query
     * @return the stable ID of the item at position
     */
    public long getItemId(int position) {
        return RecyclerView.NO_ID;
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
