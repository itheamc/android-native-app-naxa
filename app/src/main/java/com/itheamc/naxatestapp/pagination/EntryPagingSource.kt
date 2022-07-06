package com.itheamc.naxatestapp.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.itheamc.naxatestapp.models.Entry
import com.itheamc.naxatestapp.repositories.MainRepository
import com.itheamc.naxatestapp.room_db.EntryDao
import kotlinx.coroutines.delay

private const val TAG = "EntryPagingSource"

class EntryPagingSource(
    private val repository: MainRepository
) : PagingSource<Int, Entry>() {

    override fun getRefreshKey(state: PagingState<Int, Entry>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Entry> {
        val page = params.key ?: 0

        return try {
            val entities = repository.entryDao.entries(params.loadSize, page * params.loadSize)

            // For simulating the loading
            if (page != 0) delay(2500)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.d(TAG, "load: ${e.message}")
            repository.error.emit(e.message)
            LoadResult.Error(e)
        }
    }

}