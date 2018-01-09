package spencerstudios.com.codespace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import spencerstudios.com.fab_toast.FabToast;

class ListAdapter extends BaseAdapter {

    private ArrayList<Data> dataList;
    private LayoutInflater layoutInflater;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private final String SAVE_LINK = "Save to this device";
    private final String REPORT = "Report as spam";
    private final String SHARE = "Share link";

    private Context context;

    ListAdapter(Context context, ArrayList<Data> dataList) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        this.dataList = dataList;
        this.context = context;
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
        ImageView menu;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {

            view = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.menu = view.findViewById(R.id.iv_menu);
            holder.tvTitle = view.findViewById(R.id.tv_title);
            holder.tvDescription = view.findViewById(R.id.tv_description);
            holder.tvLink = view.findViewById(R.id.tv_link);
            holder.tvContributor = view.findViewById(R.id.tv_contributor);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateString = formatter.format(new Date(dataList.get(i).getTimeStamp()));

        holder.tvTitle.setText(dataList.get(i).getTitle());
        holder.tvDescription.setText(dataList.get(i).getDescription());
        holder.tvLink.setText(dataList.get(i).getLink());
        holder.tvContributor.setText("contributed by " + dataList.get(i).getContributor() + ",  "
                + dateString);

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add(SAVE_LINK);
                menu.getMenu().add(SHARE);
                menu.getMenu().add(REPORT);


                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals(SAVE_LINK)) {

                            String t = dataList.get(i).getTitle();
                            String d = dataList.get(i).getDescription();
                            String l = dataList.get(i).getLink();

                            PrefUtils.addLink(context, t, d, l);

                            FabToast.makeText(context, "link saved to this device", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                        } else if (menuItem.getTitle().equals(REPORT)) {

                            final String c = dataList.get(i).getContributor();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            final String d = formatter.format(new Date(dataList.get(i).getTimeStamp()));

                            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Report As Spam");
                            alertDialog.setMessage("Report this contribution by " + c + " that was submitted on "+d +", are you sure?");

                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes, Report", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    ReportSpam reportSpam = new ReportSpam(dataList.get(i).getTitle(), dataList.get(i).getContributor(), dataList.get(i).getTimeStamp());
                                    myRef.child("report").push().setValue(reportSpam).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FabToast.makeText(context, "thank you, this contribution has been successfully reported", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                                            } else {
                                                FabToast.makeText(context, "oops, something went wrong", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                            }
                                        }
                                    });
                                }
                            });

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();


                        }else if (menuItem.getTitle().equals(SHARE)){
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, dataList.get(i).getLink());
                            sendIntent.setType("text/plain");
                            context.startActivity(sendIntent);
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
