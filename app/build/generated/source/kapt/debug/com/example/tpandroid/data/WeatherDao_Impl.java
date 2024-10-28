package com.example.tpandroid.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.tpandroid.data.model.FavoriteCity;
import com.example.tpandroid.data.model.WeatherEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntity> __insertionAdapterOfWeatherEntity;

  private final EntityInsertionAdapter<FavoriteCity> __insertionAdapterOfFavoriteCity;

  private final EntityDeletionOrUpdateAdapter<FavoriteCity> __deletionAdapterOfFavoriteCity;

  private final SharedSQLiteStatement __preparedStmtOfClearOldCache;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntity = new EntityInsertionAdapter<WeatherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather_cache` (`location`,`latitude`,`longitude`,`weatherData`,`timestamp`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherEntity entity) {
        if (entity.getLocation() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getLocation());
        }
        statement.bindDouble(2, entity.getLatitude());
        statement.bindDouble(3, entity.getLongitude());
        if (entity.getWeatherData() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getWeatherData());
        }
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__insertionAdapterOfFavoriteCity = new EntityInsertionAdapter<FavoriteCity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_cities` (`cityName`,`latitude`,`longitude`,`country`,`cityData`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteCity entity) {
        if (entity.getCityName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCityName());
        }
        statement.bindDouble(2, entity.getLatitude());
        statement.bindDouble(3, entity.getLongitude());
        if (entity.getCountry() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCountry());
        }
        if (entity.getCityData() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCityData());
        }
      }
    };
    this.__deletionAdapterOfFavoriteCity = new EntityDeletionOrUpdateAdapter<FavoriteCity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorite_cities` WHERE `cityName` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteCity entity) {
        if (entity.getCityName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCityName());
        }
      }
    };
    this.__preparedStmtOfClearOldCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM weather_cache WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWeather(final WeatherEntity weather,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherEntity.insert(weather);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCity(final FavoriteCity city, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteCity.insert(city);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object removeFavoriteCity(final FavoriteCity city,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteCity.handle(city);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearOldCache(final long expirationTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearOldCache.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, expirationTime);
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
          __preparedStmtOfClearOldCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getWeather(final double lat, final double lon, final double tolerance,
      final Continuation<? super WeatherEntity> $completion) {
    final String _sql = "SELECT * FROM weather_cache \n"
            + "    WHERE latitude BETWEEN ? - ? AND ? + ? \n"
            + "    AND longitude BETWEEN ? - ? AND ? + ? \n"
            + "    LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 8);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, lat);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, tolerance);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, lat);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, tolerance);
    _argIndex = 5;
    _statement.bindDouble(_argIndex, lon);
    _argIndex = 6;
    _statement.bindDouble(_argIndex, tolerance);
    _argIndex = 7;
    _statement.bindDouble(_argIndex, lon);
    _argIndex = 8;
    _statement.bindDouble(_argIndex, tolerance);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WeatherEntity>() {
      @Override
      @Nullable
      public WeatherEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfWeatherData = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherData");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final WeatherEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpWeatherData;
            if (_cursor.isNull(_cursorIndexOfWeatherData)) {
              _tmpWeatherData = null;
            } else {
              _tmpWeatherData = _cursor.getString(_cursorIndexOfWeatherData);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new WeatherEntity(_tmpLocation,_tmpLatitude,_tmpLongitude,_tmpWeatherData,_tmpTimestamp);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFavoriteCities(final Continuation<? super List<FavoriteCity>> $completion) {
    final String _sql = "SELECT * FROM favorite_cities";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavoriteCity>>() {
      @Override
      @NonNull
      public List<FavoriteCity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCityData = CursorUtil.getColumnIndexOrThrow(_cursor, "cityData");
          final List<FavoriteCity> _result = new ArrayList<FavoriteCity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteCity _item;
            final String _tmpCityName;
            if (_cursor.isNull(_cursorIndexOfCityName)) {
              _tmpCityName = null;
            } else {
              _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpCountry;
            if (_cursor.isNull(_cursorIndexOfCountry)) {
              _tmpCountry = null;
            } else {
              _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            }
            final String _tmpCityData;
            if (_cursor.isNull(_cursorIndexOfCityData)) {
              _tmpCityData = null;
            } else {
              _tmpCityData = _cursor.getString(_cursorIndexOfCityData);
            }
            _item = new FavoriteCity(_tmpCityName,_tmpLatitude,_tmpLongitude,_tmpCountry,_tmpCityData);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
