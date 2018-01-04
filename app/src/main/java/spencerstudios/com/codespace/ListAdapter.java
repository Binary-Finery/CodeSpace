package spencerstudios.com.codespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ListAdapter extends BaseAdapter {

    private ArrayList<Data> dataList;
    private LayoutInflater layoutInflater;

    ListAdapter(Context context, ArrayList<Data> dataList) {
        this.dataList = dataList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView tvTitle, tvDescription, tvLink, tvContributor;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {

            view = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.tvTitle = view.findViewById(R.id.tv_title);
            holder.tvDescription = view.findViewById(R.id.tv_description);
            holder.tvLink = view.findViewById(R.id.tv_link);
            holder.tvContributor = view.findViewById(R.id.tv_contributor);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(dataList.get(i).getTitle());
        holder.tvDescription.setText(dataList.get(i).getDescription());
        holder.tvLink.setText(dataList.get(i).getLink());
        holder.tvContributor.setText("Contributor: " + dataList.get(i).getContributor() + "\n"
                +String.valueOf(dataList.get(i).getTimeStamp()));

        return view;
    }
}
