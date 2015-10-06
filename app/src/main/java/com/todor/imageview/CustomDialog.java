package com.todor.imageview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import android.app.DialogFragment;

public class CustomDialog extends DialogFragment {

    String currentFolder;
    private ListView foldersListView;
    private DialogAdapter adapter;
    private List<String> folders;
    private TextView currentPath;
    private ImageButton backButton;
    private File currentFile;
    private Button okButton;
    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = View.inflate(getActivity(), R.layout.dialog, null);
        currentPath = (TextView) v.findViewById(R.id.currentFolder);
        foldersListView = (ListView) v.findViewById(R.id.subFolders);
        backButton = (ImageButton) v.findViewById(R.id.backButton);
        okButton = (Button) v.findViewById(R.id.okButton);
        builder.setView(v);


        folders = getSubFolders("");
        adapter = new DialogAdapter(getActivity(), folders);
        foldersListView.setAdapter(adapter);
        dialogListener = (DialogListener) getTargetFragment();

        foldersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> subFolders = getSubFolders(folders.get(position));
                currentFolder = folders.get(position);
                folders.clear();
                folders.addAll(subFolders);
                adapter.notifyDataSetChanged();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.getString(currentFolder, currentFile.getPath());
                dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> subFolders = getSubFolders(currentFile.getParent());
                folders.clear();
                folders.addAll(subFolders);
                adapter.notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    public List<String> getSubFolders(String folder) {
        List<String> folders = new ArrayList<>();
        if (folder.equals("")) {
            currentFile = new File(android.os.Environment.getExternalStorageDirectory(), "");
        } else {
            currentFile = new File(android.os.Environment.getExternalStorageDirectory(), folder);
        }
        currentPath.setText(currentFile.getPath());

        if (currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    folders.add(file.getName());
                }
            }
        }
        return folders;
    }
}