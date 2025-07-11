create table eas_base_course
(
    id         int unsigned auto_increment comment '课程id'
        primary key,
    coursename varchar(100) not null comment '课程名',
    synopsis   varchar(255) null comment '课程简介'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_class
(
    id      int unsigned auto_increment
        primary key,
    classes varchar(255) not null comment '班级'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_notice
(
    id          int unsigned auto_increment
        primary key,
    title       varchar(255)  not null comment '标题',
    author      varchar(255)  not null comment '作者',
    content     varchar(1000) not null comment '内容',
    type        int default 3 not null comment '权限',
    releasedate date          not null comment '发布日期'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_permission
(
    id         int          not null comment '功能id'
        primary key,
    text       varchar(255) not null comment '功能名称',
    type       varchar(255) not null comment '功能类型',
    url        varchar(255) null comment '路径',
    percode    varchar(255) not null comment '别名',
    parentid   int          null comment '父级编号',
    sortstring int          null comment '进行排序',
    available  int          null comment '是否启用'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_role
(
    id        int auto_increment comment '角色id'
        primary key,
    name      varchar(255)  not null comment '角色名称',
    available int default 0 not null comment '是否启用'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_role_permission
(
    id                int auto_increment
        primary key,
    eas_role_id       int not null comment '角色id',
    eas_permission_id int not null comment '功能id'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_user
(
    id       int auto_increment comment '用户id'
        primary key,
    username varchar(255) not null comment '账号',
    password varchar(255) not null comment '密码',
    salt     varchar(255) not null comment '盐值',
    locked   varchar(255) not null comment '是否锁定',
    constraint username
        unique (username)
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_student
(
    id       int unsigned auto_increment
        primary key,
    username varchar(255) not null comment '账号',
    name     varchar(255) not null comment '姓名',
    sex      varchar(255) null comment '性别',
    birthday date         null comment '出生日期',
    phone    varchar(255) null comment '电话号码',
    class_id int unsigned null comment '班级id',
    motto    varchar(255) null comment '座右铭',
    constraint class_msg
        foreign key (class_id) references eas_class (id)
            on update cascade on delete cascade,
    constraint user_name
        foreign key (username) references eas_user (username)
            on update cascade on delete cascade
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_teacher
(
    id        int unsigned auto_increment
        primary key,
    username  varchar(255) not null comment '用户名',
    name      varchar(255) not null comment '教师姓名',
    sex       varchar(255) null comment '性别',
    birthday  date         null comment '出生年月',
    phone     varchar(255) null comment '电话',
    education varchar(255) null comment '学历',
    motto     varchar(255) null comment '座右铭',
    constraint username
        foreign key (username) references eas_user (username)
            on update cascade on delete cascade
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_course
(
    id             int unsigned auto_increment
        primary key,
    start_date     date                     null comment '开设日期',
    end_date       date                     null comment '结束日期',
    class_hour     smallint                 null comment '总课时',
    test_mode      varchar(255)             null comment '考核方式',
    student_num    int                      null comment '学生数量',
    choice_num     int          default 0   null comment '选课人数',
    complete       int unsigned default '0' null comment '是否是完成的课程',
    t_id           int unsigned             not null comment '外键-教师号',
    base_course_id int unsigned             not null comment '外键-课程号',
    constraint course_mag
        foreign key (base_course_id) references eas_base_course (id)
            on update cascade on delete cascade,
    constraint techer_msg
        foreign key (t_id) references eas_teacher (id)
            on update cascade on delete cascade
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table eas_score
(
    id     int unsigned auto_increment
        primary key,
    score  int default 0 not null comment '考试分数',
    result varchar(255)  null comment '考试结果',
    s_id   int unsigned  not null comment '学生id',
    c_id   int unsigned  not null comment '课程id',
    constraint course_msg
        foreign key (c_id) references eas_course (id)
            on update cascade on delete cascade,
    constraint student_msg
        foreign key (s_id) references eas_student (id)
            on update cascade on delete cascade
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index user_name
    on eas_teacher (username);

create index user_name
    on eas_user (username);

create table eas_user_role
(
    id          int auto_increment
        primary key,
    eas_user_id int              not null comment '用户id',
    eas_role_id int default 1000 not null comment '角色id',
    constraint roleid
        foreign key (eas_role_id) references eas_role (id)
            on update cascade on delete cascade,
    constraint userid
        foreign key (eas_user_id) references eas_user (id)
            on update cascade on delete cascade
)
    charset = utf8mb3
    row_format = DYNAMIC;

