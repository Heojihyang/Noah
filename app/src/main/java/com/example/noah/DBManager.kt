package com.example.noah

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context
) : SQLiteOpenHelper(context, "Noah", null, 1) {

    companion object {

        private const val TABLE_BOARD = "board"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENTS = "contents"
        private const val COLUMN_USER_ID = "user_id"

        private const val TABLE_COMMENTS = "commentsDB"
        private const val COLUMN_COMMENTS_ID = "id"
        private const val COLUMN_BOARD_ID = "board_id"
        private const val COLUMN_COMMENTS = "comments"
        private const val COLUMN_COMMENTS_USER_ID = "comments_user_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // 테이블 board 생성
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_BOARD (" +
                "$COLUMN_ID LONG , " +
                "$COLUMN_USER_ID LONG ,"+
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_CONTENTS TEXT," +
                "PRIMARY KEY ($COLUMN_ID, $COLUMN_USER_ID))")

        // 테이블 commentsDB 생성
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_COMMENTS (" +
                "$COLUMN_COMMENTS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BOARD_ID LONG, " +
                "$COLUMN_USER_ID LONG, " +
                "$COLUMN_COMMENTS TEXT, " +
                "$COLUMN_COMMENTS_USER_ID LONG, " +
                "FOREIGN KEY($COLUMN_BOARD_ID) REFERENCES $TABLE_BOARD($COLUMN_ID),"+
                "FOREIGN KEY($COLUMN_USER_ID) REFERENCES $TABLE_BOARD($COLUMN_USER_ID))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 버전이 변경되었을 때 업그레이드 로직
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_COMMENTS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BOARD")
        onCreate(db)
    }
}