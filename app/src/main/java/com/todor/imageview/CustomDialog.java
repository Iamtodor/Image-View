package com.todor.imageview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CustomDialog extends DialogFragment {

    private String currentFolder;
    private Stack<File> stackFolders;
    private List<String> subFoldersList;
    private File currentFile;

    private ListView subFoldersListView;
    private DialogAdapter dialogAdapter;
    private TextView currentPathTextView;
    private Button okButton;
    private ImageButton backButton;

    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null, false);
        currentPathTextView = (TextView) v.findViewById(R.id.currentFolder);
        subFoldersListView = (ListView) v.findViewById(R.id.subFolders);
        backButton = (ImageButton) v.findViewById(R.id.backButton);
        okButton = (Button) v.findViewById(R.id.okButton);
        builder.setView(v);

        stackFolders = new Stack<>();
        currentFile = new File(android.os.Environment.getExternalStorageDirectory(), "");
        subFoldersList = getSubFolderNames(currentFile.getPath());
        dialogAdapter = new DialogAdapter(getActivity(), subFoldersList);
        subFoldersListView.setAdapter(dialogAdapter);
        dialogListener = (DialogListener) getTargetFragment();

        subFoldersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stackFolders.push(currentFile);
                List<String> subFolders = getSubFolderNames(subFoldersList.get(position));
                subFoldersList.clear();
                subFoldersList.addAll(subFolders);
                dialogAdapter.notifyDataSetChanged();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.getFile(currentFile);
                dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stackFolders.isEmpty()) {
                    Toast.makeText(getContext(), "You can not return from root folder", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> subFolders = getSubFolderNames(stackFolders.pop().getPath());
                    subFoldersList.clear();
                    subFoldersList.addAll(subFolders);
                    dialogAdapter.notifyDataSetChanged();
                }
            }
        });
        return builder.create();
    }

    public List<String> getSubFolderNames(String folder) {
        List<String> folders = new ArrayList<>();
        File file = new File(folder);
        currentFile = new File(folder);
        currentPathTextView.setText(file.getPath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    folders.add(subFile.getPath());
                }
            }
        }
        return folders;
    }
}