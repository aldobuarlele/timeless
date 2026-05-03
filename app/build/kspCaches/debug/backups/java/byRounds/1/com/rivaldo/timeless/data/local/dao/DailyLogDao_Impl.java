package com.rivaldo.timeless.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rivaldo.timeless.data.local.entity.DailyLogEntity;
import com.rivaldo.timeless.data.local.entity.MediaAttachmentEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DailyLogDao_Impl implements DailyLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyLogEntity> __insertionAdapterOfDailyLogEntity;

  private final EntityInsertionAdapter<MediaAttachmentEntity> __insertionAdapterOfMediaAttachmentEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLog;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMediaAttachmentsForDate;

  public DailyLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyLogEntity = new EntityInsertionAdapter<DailyLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_log` (`date`,`content`,`created_at`,`updated_at`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyLogEntity entity) {
        statement.bindString(1, entity.getDate());
        statement.bindString(2, entity.getContent());
        statement.bindLong(3, entity.getCreatedAt());
        statement.bindLong(4, entity.getUpdatedAt());
      }
    };
    this.__insertionAdapterOfMediaAttachmentEntity = new EntityInsertionAdapter<MediaAttachmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `media_attachment` (`id`,`log_date`,`uri`,`file_name`,`latitude`,`longitude`,`taken_at_millis`,`micro_thumbnail_base64`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MediaAttachmentEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getLogDate());
        statement.bindString(3, entity.getUri());
        statement.bindString(4, entity.getFileName());
        if (entity.getLatitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLongitude());
        }
        if (entity.getTakenAtMillis() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getTakenAtMillis());
        }
        if (entity.getMicroThumbnailBase64() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMicroThumbnailBase64());
        }
      }
    };
    this.__preparedStmtOfUpdateLog = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_log SET content = ?, updated_at = ? WHERE date = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMediaAttachmentsForDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM media_attachment WHERE log_date = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLog(final DailyLogEntity log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMediaAttachments(final List<MediaAttachmentEntity> attachments,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMediaAttachmentEntity.insert(attachments);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object saveLogWithAttachments(final DailyLogEntity log,
      final List<MediaAttachmentEntity> attachments, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> DailyLogDao.DefaultImpls.saveLogWithAttachments(DailyLogDao_Impl.this, log, attachments, __cont), $completion);
  }

  @Override
  public Object updateLog(final String date, final String content, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLog.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, content);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, date);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLog.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMediaAttachmentsForDate(final String logDate,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMediaAttachmentsForDate.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, logDate);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteMediaAttachmentsForDate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<DailyLogEntity> getLogByDate(final String date) {
    final String _sql = "SELECT * FROM daily_log WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_log"}, new Callable<DailyLogEntity>() {
      @Override
      @Nullable
      public DailyLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final DailyLogEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new DailyLogEntity(_tmpDate,_tmpContent,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DailyLogEntity>> getAllLogs() {
    final String _sql = "SELECT * FROM daily_log ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_log"}, new Callable<List<DailyLogEntity>>() {
      @Override
      @NonNull
      public List<DailyLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<DailyLogEntity> _result = new ArrayList<DailyLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyLogEntity _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new DailyLogEntity(_tmpDate,_tmpContent,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DailyLogEntity>> getLogsByWeekRange(final String startDate,
      final String endDate) {
    final String _sql = "SELECT * FROM daily_log WHERE date >= ? AND date <= ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindString(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_log"}, new Callable<List<DailyLogEntity>>() {
      @Override
      @NonNull
      public List<DailyLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<DailyLogEntity> _result = new ArrayList<DailyLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyLogEntity _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new DailyLogEntity(_tmpDate,_tmpContent,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<MediaAttachmentEntity>> getMediaAttachmentsForDate(final String logDate) {
    final String _sql = "SELECT * FROM media_attachment WHERE log_date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, logDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"media_attachment"}, new Callable<List<MediaAttachmentEntity>>() {
      @Override
      @NonNull
      public List<MediaAttachmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLogDate = CursorUtil.getColumnIndexOrThrow(_cursor, "log_date");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "file_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTakenAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "taken_at_millis");
          final int _cursorIndexOfMicroThumbnailBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "micro_thumbnail_base64");
          final List<MediaAttachmentEntity> _result = new ArrayList<MediaAttachmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaAttachmentEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpLogDate;
            _tmpLogDate = _cursor.getString(_cursorIndexOfLogDate);
            final String _tmpUri;
            _tmpUri = _cursor.getString(_cursorIndexOfUri);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Long _tmpTakenAtMillis;
            if (_cursor.isNull(_cursorIndexOfTakenAtMillis)) {
              _tmpTakenAtMillis = null;
            } else {
              _tmpTakenAtMillis = _cursor.getLong(_cursorIndexOfTakenAtMillis);
            }
            final String _tmpMicroThumbnailBase64;
            if (_cursor.isNull(_cursorIndexOfMicroThumbnailBase64)) {
              _tmpMicroThumbnailBase64 = null;
            } else {
              _tmpMicroThumbnailBase64 = _cursor.getString(_cursorIndexOfMicroThumbnailBase64);
            }
            _item = new MediaAttachmentEntity(_tmpId,_tmpLogDate,_tmpUri,_tmpFileName,_tmpLatitude,_tmpLongitude,_tmpTakenAtMillis,_tmpMicroThumbnailBase64);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
