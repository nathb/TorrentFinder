package com.nathb.torrentfinder.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.loader.AbstractLoader;
import com.nathb.torrentfinder.loader.ShowListLoader;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.task.AddShowTask;
import com.nathb.torrentfinder.task.DeleteShowTask;
import com.nathb.torrentfinder.ui.adapter.ShowListAdapter;
import com.nathb.torrentfinder.ui.dialog.AddShowDialogFragment;

import java.util.ArrayList;

public class ShowListActivity extends AbstractListLoaderActivity<Show> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_show_list;
    }

    @Override
    protected ArrayAdapter createAdapter() {
        return new ShowListAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<Show>());
    }

    @Override
    protected AbstractLoader createLoader() {
        return new ShowListLoader(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final Show show = (Show) v.getTag();
        final Intent intent = new Intent(this, EpisodeListActivity.class);
        intent.putExtra(EpisodeListActivity.EXTRA_SHOW_ID, show.getShowId());
        intent.putExtra(EpisodeListActivity.EXTRA_SHOW_TITLE, show.getTitle());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_add:
                add();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_long_press_show, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.Delete:
                final Show show = getAdapter().getItem(info.position);
                delete(show);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected String getActionBarTitle() {
        return "TF - Shows";
    }

    private void add() {
        final AddShowDialogFragment dialog = new AddShowDialogFragment();
        dialog.show(getFragmentManager(), AddShowDialogFragment.TAG);
        dialog.setListener(new OnShowAddedListener());
    }

    private void delete(Show show) {
        new DeleteShowTask(new ShowDao(this), show, new OnShowDeletedListener()).execute();
    }

    private class OnShowDeletedListener implements DeleteShowTask.OnShowDeletedListener {

        @Override
        public void onShowDeleted() {
            load();
        }
    }

    private class OnShowAddedListener implements AddShowTask.OnShowAddedListener {

        @Override
        public void onShowAdded() {
            load();
        }
    }
}
