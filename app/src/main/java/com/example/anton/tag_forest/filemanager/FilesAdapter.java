package com.example.anton.tag_forest.filemanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anton.tag_forest.R;
import com.example.anton.tag_forest.TagDB.DatabaseManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private final LayoutInflater layoutInflater;
    private final File initialFile;

    private File[] files;
    private File currentFile;
    private Context contex;
    private DatabaseManager manager;
    private boolean flag = false;

    FilesAdapter(final Context context) {
        this.contex = context;
        layoutInflater = LayoutInflater.from(context);
        initialFile = Environment.getExternalStorageDirectory();
        setDirectory(initialFile);
        manager = DatabaseManager.getInstance(context);
    }

    private void setDirectory(final File file) {
        this.currentFile = file;
        this.files = file.listFiles();
        sortFiles(this.files);
        notifyDataSetChanged();
    }

    public boolean goBack() {
        if (currentFile.equals(initialFile) && !flag) {
            return false;
        }
        if (flag) {
            setDirectory(currentFile);
            return true;
        }
        File parent = currentFile.getParentFile();
        if (parent != null) {
            setDirectory(parent);
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(layoutInflater.inflate(R.layout.file_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.bind(files[position]);
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(contex);
//        // Get the layout inflater
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(contex.R.layout.file_action, null)
//                // Add action buttons
//                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // sign in the user ...
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
//                    }
//                });
//        return builder.create();
//    }


    @Override
    public int getItemCount() {
        return files != null ? files.length : 0;
    }

    private static void sortFiles(final File[] files) {
        if (files == null) {
            return;
        }
        Arrays.sort(files, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) return -1;
            if (f2.isDirectory() && !f1.isDirectory()) return 1;
            return f1.getName().compareTo(f2.getName());
        });
    }

    public void update(String query) {
        List<File> result = new ArrayList<>();
        update(result, files, query);
        files = result.toArray(new File[0]);
        sortFiles(files);
        notifyDataSetChanged();
        flag = true;
    }

    private void update(List<File> result, File[] files, String query) {
        for (File file : files) {
            if (file.isDirectory()) {
                update(result, file.listFiles(), query);
            } else if (file.getName().toLowerCase().contains(query)) {
                result.add(file);
            }
        }
    }


    final class FileViewHolder extends RecyclerView.ViewHolder {
        private final TextView filename;
        private final TextView iconFile;
        private final TextView iconFolder;

        FileViewHolder(View view) {
            super(view);
            filename = view.findViewById(R.id.filename);
            iconFile = view.findViewById(R.id.file_icon);
            iconFolder = view.findViewById(R.id.folder_icon);
        }

        String getMimeType(String url) {
            String type = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            return type;
        }

        @SuppressLint("SetTextI18n")
        void bind(final File file) {
            if (file.isDirectory()) {
                iconFile.setVisibility(View.GONE);
                filename.setTypeface(null, Typeface.BOLD);
                filename.setText(file.getName() + "/");
            } else {
                iconFolder.setVisibility(View.GONE);
                filename.setTypeface(null, Typeface.NORMAL);
                filename.setText(file.getName());
            }

            itemView.setOnClickListener(v -> {
                if (file.isDirectory()) {
                    FilesAdapter.this.setDirectory(file);
                } else {
                    try {
                        Uri path = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(path, getMimeType(file.getPath()));
                        contex.startActivity(intent);
                    } catch (ActivityNotFoundException ignored) {

                    }
                }
            });
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(v -> {
                if (file.isFile()) {
                    LayoutInflater factory = LayoutInflater.from(contex);
                    @SuppressLint("InflateParams") final View textEntryView = factory.inflate(R.layout.file_action, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(contex);
                    builder.setView(textEntryView)
                            .setPositiveButton("Добавить",
                                    (dialog, id) -> {
                                        EditText editor = textEntryView.findViewById(R.id.tagmane);
                                        Toast.makeText(contex, editor.getText(), Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    })
                            .setNegativeButton("Отменить",
                                    (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                } else {
                    return false;
                }
            });
        }
    }
}
