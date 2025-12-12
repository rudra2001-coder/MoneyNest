package com.rudra.moneynest.ui.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@HiltViewModel
class DataExportViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun exportToCsv() {
        viewModelScope.launch {
            val transactions = repository.getAllTransactions().first()
            val file = File(context.cacheDir, "transactions.csv")
            FileWriter(file).use { writer ->
                writer.append("Amount,Type,Category,Date,Notes\n")
                transactions.forEach { transaction ->
                    writer.append("${transaction.amount},${transaction.type},${transaction.category},${transaction.date},${transaction.notes}\n")
                }
            }
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Export Transactions"))
        }
    }
}
