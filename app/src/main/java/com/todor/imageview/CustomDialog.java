package com.todor.imageview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

public class CustomDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog, null, false));
        ArrayList<String> subFoldersList = new ArrayList<>();

        View v = View.inflate(getActivity(), R.layout.dialog, null);
        TextView currentPath = (TextView) v.findViewById(R.id.currentFolder);
        ListView subFolders = (ListView) v.findViewById(R.id.subFolders);

        File currentFile = new File(android.os.Environment.getExternalStorageDirectory(), "");
        Log.d("Current directory is: ", currentFile.getPath());
        currentPath.setText(currentFile.getPath());

        if(currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            for(File file : files) {
                if(file.isDirectory()) {
                    Log.d("Current subfolder is: ", file.getPath());
                    subFoldersList.add(file.getName());
                }
            }
        }

        DialogAdapter adapter = new DialogAdapter(getActivity(), subFoldersList);
        subFolders.setAdapter(adapter);
        return builder.create();
    }
}
