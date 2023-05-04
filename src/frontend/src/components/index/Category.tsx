import { Box, Button, Flex, createStyles, rem } from "@mantine/core";
import useCategories from "../../hooks/useCategories";
import CategorySkeleton from "./CategorySkeleton";

const useStyles = createStyles((theme) => ({
  category: {
    marginTop: rem(3),
    padding: rem(15),
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[2]
    }`,
    backgroundColor: "#ffffff",
  },
  badge: {
    marginTop: rem(5),
    borderRadius: 0,
    fontSize: rem(14),
    marginRight: rem(30),
  },
}));

interface Props {
  currentCategory?: string | null;
  setCategory: (category: string) => void;
}

const Category = ({ currentCategory, setCategory }: Props) => {
  const { classes } = useStyles();

  const { data, isLoading } = useCategories();
  const skeleton = [1, 2, 3, 4, 5];

  return (
    <Flex align="center" className={classes.category}>
      {currentCategory == null || currentCategory === "" ? (
        <Button
          className={classes.badge}
          variant="gradient"
          gradient={{ from: "indigo", to: "cyan" }}
          onClick={() => setCategory("")}
          size="xs"
        >
          All
        </Button>
      ) : (
        <Button
          className={classes.badge}
          variant="light"
          gradient={{ from: "indigo", to: "cyan" }}
          onClick={() => setCategory("")}
          size="xs"
        >
          {" "}
          All
        </Button>
      )}
      {isLoading && skeleton.map(() => <CategorySkeleton />)}

      {data.data &&
        data.data.map((category, key) => {
          if (currentCategory === category.category) {
            return (
              <Button
                key={key}
                className={classes.badge}
                variant="gradient"
                onClick={() => {
                  console.log("cate clicked");
                  setCategory(category.category);
                }}
                size="xs"
              >
                {category.category}
              </Button>
            );
          }

          return (
            <Button
              key={key}
              className={classes.badge}
              onClick={() => {
                console.log("cate clicked");
                setCategory(category.category);
              }}
              variant="light"
              size="xs"
            >
              {category.category}
            </Button>
          );
        })}
    </Flex>
  );
};

export default Category;
