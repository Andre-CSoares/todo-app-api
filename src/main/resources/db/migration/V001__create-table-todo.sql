create table Todo(
    id int not null auto_increment,
    nome varchar(255) not null,

    primary key (id)
);

alter table Todo
    add constraint uk_todo unique (nome);