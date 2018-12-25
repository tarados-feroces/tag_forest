package com.example.anton.tag_forest.filemanager;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.anton.tag_forest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private final LayoutInflater layoutInflater;
    private final File initialFile;

    private File[] files;
    private File currentFile;
    private Context contex;

    FilesAdapter(final Context context) {
        this.contex = context;
        layoutInflater = LayoutInflater.from(context);
        initialFile = Environment.getExternalStorageDirectory();
        setDirectory(initialFile);
    }

    private void setDirectory(final File file) {
        this.currentFile = file;
        this.files = file.listFiles();
        sortFiles(this.files);
        notifyDataSetChanged();
    }

    public boolean goBack() {
        if (currentFile.equals(initialFile)) {
            return false;
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
        files = result.toArray(new File[result.size()]);
        notifyDataSetChanged();
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
        }
    }
}
