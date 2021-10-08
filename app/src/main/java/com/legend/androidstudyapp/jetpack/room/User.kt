package com.legend.androidstudyapp.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * date：2021/9/28 16:11
 * @author wanglezhi
 * desc:
 *  这个类会描述用于保存字词的实体（代表 SQLite 表）。类中的每个属性代表表中的一列。Room 最终会使用这些属性来创建表并将数据库行中的对象实例化。
 *
 *  @Entity 类代表一个 SQLite 表。为您的类声明添加注解，以表明它是实体。如果您希望表的名称与类的名称不同，可以指定表的名称，此处的表名为“user_table”
 *  @PrimaryKey 每个实体都需要主键。为简便起见，每个字词都可充当自己的主键。
 *  @ColumnInfo(name = "first_name") 如果您希望该表中列的名称与成员变量的名称不同，可以指定表中列的名称
 */
@Entity(tableName = "user_table")
data class User (@PrimaryKey val uid: Int,
                 @ColumnInfo(name = "first_name") val firstName: String?,
                 @ColumnInfo(name = "last_name") val lastName: String?)