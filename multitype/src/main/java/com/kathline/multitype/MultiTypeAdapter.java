package com.kathline.multitype;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract class to create multi type of {@link RecyclerView.Adapter}.
 */
public abstract class MultiTypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Class<?>> dataCache = new ArrayList<>();// [DataType]
    private SparseArray<SparseArray<ViewTypeCreator<?, ?>>> creatorCache = new SparseArray<>();// DataTypeIndex - [ViewTypeCreators]
    private SparseArray<ViewTypeCreator<?, ?>> viewTypeCache = new SparseArray<>();// ViewType - ViewTypeCreator

    public abstract Object getData(int position);

    /**
     * Register a viewTypeCreator to create a view for this data type.
     *
     * @param creator A viewTypeCreator instance.
     */
    public <T> void registerCreator(ViewTypeCreator<T, ?>... creator) {
        for (int i = 0; i < creator.length; i++) {
            registerCreatorInner(creator[i].getTClass(), creator[i]);
        }
    }
    /**
     * A data may bind more than one viewTypeCreator, so we create an identityHashCode to
     * mark every viewTypeCreator for the same data type.
     *
     * @param clazz   The type of data.
     * @param creator The viewTypeCreator to bind this data type.
     */
    private <T> void registerCreatorInner(Class<?> clazz, ViewTypeCreator<T, ?> creator) {
        int index = dataCache.indexOf(clazz);
        if (index == -1) {
            dataCache.add(clazz);
            index = dataCache.size() - 1;
        }
        SparseArray<ViewTypeCreator<?, ?>> cache = creatorCache.get(index);
        if (cache == null) {
            cache = new SparseArray<>();
        }
        int id = System.identityHashCode(creator);
        cache.put(id, creator);
        creatorCache.put(index, cache);
    }

    @Override
    public int getItemViewType(int position) {
        Object data = getData(position);
        int viewType = getCreatorViewType(data);
        if (viewType != -1) {
            return viewType;
        } else
            return super.getItemViewType(position);
    }


    @Override
    public long getItemId(int position) {
        int itemViewType = getItemViewType(position);
        ViewTypeCreator<?, ?> viewCreator = getViewCreatorByViewType(itemViewType);
        return viewCreator.getItemId(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewTypeCreator<?, ?> viewCreator = getViewCreatorByViewType(viewType);
        return viewCreator.onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        ViewTypeCreator viewCreator = getViewCreatorByViewType(getItemViewType(position));
        viewCreator.onBindViewHolder(holder, position, getData(position), payloads);
    }

    /**
     * Find the viewType by a data instance.
     * If the Data bind more than one viewTypeCreator, we use function <match> to mark the viewTypeCreator which you expect want.
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
     * @param data The data instance.
     * @return The viewType.
     */

    private int getCreatorViewType(Object data) {
        Class<?> clazz = data.getClass();
        int viewType;
        int index = dataCache.indexOf(clazz);
        if (dataCache.size() > 0 && index != -1) {
            SparseArray<ViewTypeCreator<?, ?>> creators = creatorCache.get(index);
            if (creators.size() > 1) {
                // The Data bind more than one viewTypeCreator.
                for (int i = 0; i < creators.size(); i++) {
                    int key = creators.keyAt(i);
                    ViewTypeCreator viewCreator = creators.get(key);
                    if (viewCreator.match(data)) {
                        viewType = key;
                        if (viewTypeCache.indexOfKey(viewType) < 0) {
                            viewTypeCache.put(viewType, viewCreator);
                        }
                        return viewType;
                    }
                }
            } else if (creators.size() == 1) {
                // The Data only bind one viewTypeCreator.
                viewType = creators.keyAt(0);
                if (viewTypeCache.indexOfKey(viewType) < 0) {
                    viewTypeCache.put(viewType, creators.valueAt(0));
                }
                return viewType;
            }
        }
        throw new RuntimeException("Current dataType [" + clazz + "] is not found in DataTypeCache:\n" + this.dataCache + " \nPlease check the Type of data for your custom creator.");
    }

    /**
     * Find the viewTypeCreator by current viewType.
     *
     * @param viewType Current viewType.
     * @return The ViewTypeCreator.
     */
    private ViewTypeCreator<?, ?> getViewCreatorByViewType(int viewType) {
        return viewTypeCache.get(viewType);
    }

}