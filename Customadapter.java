package proveb.gk.com.sqlite.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import proveb.gk.com.sqlite.R;
import proveb.gk.com.sqlite.modelclass.Jsonmodel;

/**
 * Created by Nehru on 02-07-2016.
 */
public class Customadapter extends BaseAdapter implements Filterable {
    private Activity context;
    ArrayList<Jsonmodel> jsonmodelArrayList;
    ArrayList<Jsonmodel> originalArrayList;
    private LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    public Customadapter(Activity context, ArrayList<Jsonmodel> jsonmodelArrayList) {
        this.context = context;
        this.jsonmodelArrayList = jsonmodelArrayList;
        this.originalArrayList = jsonmodelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return jsonmodelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonmodelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.jsonlist, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.doc_id = (TextView) view.findViewById(R.id.tv_doc_id);
            myViewHolder.fullname = (TextView) view.findViewById(R.id.tv_fullname);
            myViewHolder.doc_image = (ImageView) view.findViewById(R.id.imv_doc_image);
//            Glide.with(context).load(jsonmodelArrayList.get(position).getImage_url()).into(myViewHolder.doc_image);

            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        myViewHolder.doc_id.setText(jsonmodelArrayList.get(position).getDoc_id());
        myViewHolder.fullname.setText(jsonmodelArrayList.get(position).getFullname());
//        myViewHolder.fullname.setText(jsonmodelArrayList.get(position).getImage_url());
        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }


    private class MyViewHolder {
        private TextView doc_id, fullname;
        private ImageView doc_image;
    }

    public class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList <Jsonmodel> list = originalArrayList;

            final ArrayList<Jsonmodel> nlist = new ArrayList<Jsonmodel>(list.size());

            Jsonmodel filterableString;

            for (int i = 0; i < list.size(); i++) {
                filterableString = list.get(i);
                if (filterableString.getFullname().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            jsonmodelArrayList = (ArrayList<Jsonmodel>) results.values;
            notifyDataSetChanged();
        }

    }

}
