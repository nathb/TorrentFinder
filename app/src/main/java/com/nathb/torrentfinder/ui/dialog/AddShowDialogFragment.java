package com.nathb.torrentfinder.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.task.AddShowTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;

public class AddShowDialogFragment extends DialogFragment {

    public static final String TAG = AddShowDialogFragment.class.getSimpleName();

    @InjectView(R.id.TitleInput) EditText mTitleInput;
    @InjectView(R.id.TorrentSeachTermInput) EditText mTorrentSearchTermInput;
    @InjectView(R.id.EpisodeListTermInput) EditText mEpisodeListTermInput;

    private AddShowTask.OnShowAddedListener mListener;

    public void setListener(AddShowTask.OnShowAddedListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final View view = activity.getLayoutInflater().inflate(R.layout.dialog_add_show, null);
        ButterKnife.inject(this, view);

        final AlertDialog alertDialog  = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_add_show_title)
                .setView(view)
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        // Set button listener here to prevent onClick automatically dismissing
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSaveClicked(alertDialog);
                    }
                });
            }
        });

        return alertDialog;
    }

    @OnTextChanged(R.id.TitleInput)
    void onTitleInputChanged() {
        // Keep Torrent and Episode edit boxes in sync with title
        // input if not modified as they will most likely be the same
        final String titleText = mTitleInput.getText().toString();
        if (TextUtils.isEmpty(titleText)) {
            return;
        }

        final String previousTitleText = titleText.substring(0, titleText.length() - 1);
        final String torrentText = mTorrentSearchTermInput.getText().toString();
        if ((TextUtils.isEmpty(torrentText) || previousTitleText.equals(torrentText))) {
            mTorrentSearchTermInput.setText(titleText);
        }

        final String episodeText = mEpisodeListTermInput.getText().toString();
        if (TextUtils.isEmpty(episodeText) || previousTitleText.equals(episodeText)) {
            mEpisodeListTermInput.setText(titleText);
        }
    }

    private void onSaveClicked(AlertDialog alertDialog) {
        if (validAllFields()) {
            final Show show = new Show(
                    mTitleInput.getText().toString(),
                    mTorrentSearchTermInput.getText().toString(),
                    mEpisodeListTermInput.getText().toString());
            new AddShowTask(new ShowDao(getActivity()), show, mListener).execute();
            alertDialog.dismiss();
        }
    }

    private boolean validAllFields() {
        boolean valid = true;
        valid &= validateField(mTitleInput);
        valid &= validateField(mTorrentSearchTermInput);
        valid &= validateField(mEpisodeListTermInput);
        return valid;
    }

    private boolean validateField(EditText field) {
        boolean valid = true;
        if (TextUtils.isEmpty(field.getText())) {
            valid = false;
            field.setBackgroundColor(Color.RED);
        } else {
            field.setBackgroundColor(Color.TRANSPARENT);
        }
        return valid;
    }

}
