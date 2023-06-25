package com.mobile.machinelearning.translationapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.mobile.machinelearning.translationapp.adapter.HistoryAdapter;
import com.mobile.machinelearning.translationapp.R;
import com.mobile.machinelearning.translationapp.database.HistoryDatabase;
import com.mobile.machinelearning.translationapp.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private SearchView searchView;
    private List<HistoryItem> historyItems;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Thêm dữ liệu vào historyItems
        historyItems = HistoryDatabase.getInstance(this).historyDao().findAll();
        recyclerView = findViewById(R.id.recycler_view_search_history);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new HistoryAdapter(historyItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        btnHome = findViewById(R.id.home);
        // Back to home
        btnHome.setOnClickListener(v -> openMainActivity());

    }

    // Tìm kiếm lịch sử dịch
    private void filterList(String keyword) {
        List<HistoryItem> filteredList = new ArrayList<>();
        for (HistoryItem item: historyItems) {
            if (item.getKeyword().toLowerCase().contains(keyword.toLowerCase()) || item.getResult().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setFilteredList(filteredList);
        }
    }
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}