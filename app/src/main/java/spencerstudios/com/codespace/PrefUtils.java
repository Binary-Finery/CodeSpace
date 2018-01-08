package spencerstudios.com.codespace;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefUtils {

    private static final String LINKS = "links";

    static ArrayList<SavedLinksData> links(Context context) {
        ArrayList<SavedLinksData> temp;
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String content = db.getString(LINKS, "");

        if (content.isEmpty()) {
            temp = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<SavedLinksData>>() {
            }.getType();
            temp = gson.fromJson(content, type);
        }
        return temp;
    }

    static void addLink(Context context, String... s){
        List<SavedLinksData> temp = links(context);
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = db.edit();
        temp.add(0, new SavedLinksData(s[0], s[1], s[2]));
        Gson gson = new Gson();
        String dbs = gson.toJson(temp);
        editor.putString(LINKS, dbs).apply();
    }

    static void removeLink(Context context, int position){
        List<SavedLinksData> temp = links(context);
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = db.edit();
        temp.remove(position);
        Gson gson = new Gson();
        String dbs = gson.toJson(temp);
        editor.putString(LINKS, dbs).apply();
    }
}
