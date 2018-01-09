package spencerstudios.com.codespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

class SavedAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<SavedLinksData> savedLinksDataArrayList;
    private LayoutInflater layoutInflater;

    private final String SHARE = "Share link";
    private final String REMOVE = "Remove";

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
        ImageView menu;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null){
            view = layoutInflater.inflate(R.layout.list_item_saved, null);
            holder = new ViewHolder();

            holder.tvTitle = view.findViewById(R.id.tv_title);
            holder.tvDescription = view.findViewById(R.id.tv_description);
            holder.tvLink = view.findViewById(R.id.tv_link);
            holder.menu = view.findViewById(R.id.iv_menu);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(savedLinksDataArrayList.get(i).getTitle());
        holder.tvDescription.setText(savedLinksDataArrayList.get(i).getDescription());
        holder.tvLink.setText(savedLinksDataArrayList.get(i).getLink());

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add(SHARE);
                menu.getMenu().add(REMOVE);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals(SHARE)) {

                        }

                        else if (menuItem.getTitle().equals(REMOVE)){
                            PrefUtils.removeLink(context, i);
                            savedLinksDataArrayList.remove(i);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });
                menu.show();

            }
        });

        return view;
    }
}
