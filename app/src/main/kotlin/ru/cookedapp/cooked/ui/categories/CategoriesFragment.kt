package ru.cookedapp.cooked.ui.categories

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.base.ComposeFragment
import ru.cookedapp.cooked.ui.categories.data.CategoriesState
import ru.cookedapp.cooked.ui.categories.data.CategoryData
import ru.cookedapp.cooked.ui.components.ScreenScaffold
import ru.cookedapp.cooked.ui.components.renameDialog.RenameDialog
import ru.cookedapp.cooked.ui.components.renameDialog.rememberRenameDialogState
import ru.cookedapp.cooked.ui.helpers.rememberSaveableState

internal class CategoriesFragment : ComposeFragment() {

    private val viewModel: CategoriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
    }

    @Composable
    override fun Content() {
        ScreenScaffold(
            screenTitle = stringResource(R.string.categories_title),
            onBackPressed = { navController.popBackStack() },
        ) {
            val state by viewModel.state.collectAsStateWithLifecycle()

            CategoriesScreen(state)
        }
    }

    @Composable
    private fun CategoriesScreen(state: CategoriesState) {
        var isRenameDialogVisible by rememberSaveableState(false)
        val renameDialogState = rememberRenameDialogState()

        if (isRenameDialogVisible) {
            RenameDialog(
                state = renameDialogState,
                titleTextRes = R.string.categories_rename_label,
                labelTextRes = R.string.categories_name_label,
                placeholderTextRes = R.string.categories_name_placeholder,
                icon = Icons.Filled.Edit,
                onSaved = {
                    val (text, categoryId) = renameDialogState.requireStateData()
                    viewModel.onCategoryRenamed(categoryId, text)
                },
                onDismiss = {
                    isRenameDialogVisible = false
                },
            )
        }

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            state.categories.forEach { category ->
                CategoryRow(category = category) {
                    renameDialogState.updateState(
                        text = category.name,
                        subjectId = category.id,
                    )
                    isRenameDialogVisible = true
                }
            }
        }
    }

    @Composable
    private fun CategoryRow(
        category: CategoryData,
        onClick: () -> Unit,
    ) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(all = 16.dp),
    ) {
        Text(text = category.name)
    }
}
