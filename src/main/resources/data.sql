delete from tacocloud.Ingredient_Ref;
delete from tacocloud.Taco;
delete from tacocloud.Taco_Order;
delete from tacocloud.Ingredient;

insert into tacocloud.Ingredient (id, name, type)
values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into tacocloud.Ingredient (id, name, type)
values ('COTO', 'Corn Tortilla', 'WRAP');
insert into tacocloud.Ingredient (id, name, type)
values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into tacocloud.Ingredient (id, name, type)
values ('CARN', 'Carnitas', 'PROTEIN');
insert into tacocloud.Ingredient (id, name, type)
values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into tacocloud.Ingredient (id, name, type)
values ('LETC', 'Lettuce', 'VEGGIES');
insert into tacocloud.Ingredient (id, name, type)
values ('CHED', 'Cheddar', 'CHEESE');
insert into tacocloud.Ingredient (id, name, type)
values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into tacocloud.Ingredient (id, name, type)
values ('SLSA', 'Salsa', 'SAUCE');
insert into tacocloud.Ingredient (id, name, type)
values ('SRCR', 'Sour Cream', 'SAUCE');
