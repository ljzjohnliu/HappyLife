# dataroom封装数据库模块

## room数据库的升级两种方式：

### 1、作死型方式

    之所以叫作死型方式就是这种方式会清空数据库中的数据，所以要使用这种方式之前一定要慎重考虑。 如果你不想写Migration的相关代码,那么你就用下面的方式：

    @Database(entities = {User.class}, version = 3)
    public abstract class UsersDatabase extends RoomDatabase
    
    database = Room.databaseBuilder(context.getApplicationContext(),
                        UsersDatabase.class, "Sample.db")
                 //添加下面这一行 fallbackToDestructiveMigration这行代码所做的操作：所有表都会被丢弃，同时 identity_hash 被插入
                .fallbackToDestructiveMigration()
                .build();

### 2、正确姿势
    （1）修改数据库版本号
         @Database(entities = {User.class}, version = 2)
         public abstract class UsersDatabase extends RoomDatabase
    （2）创建Migration，1和2分别代表上一个版本和新的版本
         static final Migration MIGRATION_1_2 = new Migration(1, 2) {
         @Override
         public void migrate(SupportSQLiteDatabase database) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE users "
                + " ADD COLUMN last_update INTEGER");
          }
      };
    （3）把migration 添加到 Room database builder

database = Room.databaseBuilder(context.getApplicationContext(),
        UsersDatabase.class, "Sample.db")
         //增加下面这一行
        .addMigrations(MIGRATION_1_2)
        .build();
说明：SQLite的ALTER TABLE命令非常局限，只支持重命名表以及添加新的字段
