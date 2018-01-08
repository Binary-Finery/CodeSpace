package spencerstudios.com.codespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class SavedAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<SavedLinksData> savedLinksDataArrayList;
    private LayoutInflater layoutInflater;

    SavedAdapter(Context context, ArrayList<SavedLinksData> savedLinksDataArrayList){
        this.context = context;
        this.savedLinksDataArrayList = savedLinksDataArrayList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return savedLinksDataArrayList.size();
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
        TextView tvTitle, tvDescription, tvLink;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null){
            view = layoutInflater.inflate(R.layout.list_item_saved, null);
            holder = new ViewHolder();

            holder.tvTitle = view.findViewById(R.id.tv_title);
            holder.tvDescription = view.findViewById(R.id.tv_description);
            holder.tvLink = view.findViewById(R.id.tv_link);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(savedLinksDataArrayList.get(i).getTitle());
        holder.tvDescription.setText(savedLinksDataArrayList.get(i).getDescription());
        holder.tvLink.setText(savedLinksDataArrayList.get(i).getLink());

        return view;
    }
}
