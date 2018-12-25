CREATE TABLE "author" (
    "id" SERIAL PRIMARY KEY,
    "first_name" VARCHAR(255) NOT NULL,
    "last_name" VARCHAR(255) NOT NULL
);

CREATE TABLE "book" (
    "id" SERIAL PRIMARY KEY,
    "title" VARCHAR(255) NOT NULL
);

CREATE TABLE genre (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
);

CREATE TABLE "book_author" (
    "book_id" INTEGER NOT NULL,
    "author_id" INTEGER NOT NULL,
    PRIMARY KEY ("book_id", "author_id"),
    CONSTRAINT "book_author_book_fk" FOREIGN KEY ("book_id") REFERENCES "book" ("id"),
    CONSTRAINT "book_author_author_fk" FOREIGN KEY ("author_id") REFERENCES "author" ("id")
);

CREATE TABLE "book_genre" (
    "book_id" INTEGER NOT NULL,
    "genre_id" INTEGER NOT NULL,
    PRIMARY KEY ("book_id", "genre_id"),
    CONSTRAINT "book_genre_book_fk" FOREIGN KEY ("book_id") REFERENCES "book" ("id"),
    CONSTRAINT "book_genre_genre_fk" FOREIGN KEY ("genre_id") REFERENCES "genre" ("id")
);