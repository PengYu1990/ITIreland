import { Badge, Box, Container, createStyles, rem } from "@mantine/core";
import useCategory from "../hooks/useCategory";

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
    padding: rem(12),
    marginTop: rem(5),
    borderRadius: 0,
    fontSize: rem(14),
    marginRight: rem(30),
  },
}));

const Category = () => {
  const { classes } = useStyles();

  const { data, error, isLoading } = useCategory();
  return (
    <Box className={classes.category}>
      <Badge
        className={classes.badge}
        variant="gradient"
        gradient={{ from: "indigo", to: "cyan" }}
      >
        All
      </Badge>
      {data &&
        data.map((category, key) => (
          <Badge key={key} className={classes.badge}>
            {category.category}
          </Badge>
        ))}
    </Box>
  );
};

export default Category;
