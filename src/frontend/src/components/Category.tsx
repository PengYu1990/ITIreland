import { Badge, Box, Container, createStyles, rem } from "@mantine/core";

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
  const active = false;
  return (
    <Box className={classes.category}>
      <Badge
        className={classes.badge}
        variant="gradient"
        gradient={{ from: "indigo", to: "cyan" }}
      >
        Java
      </Badge>
      <Badge className={classes.badge}>Python</Badge>
      <Badge className={classes.badge}>Node.js</Badge>
      <Badge className={classes.badge}>React</Badge>
      <Badge className={classes.badge}>C#</Badge>
      <Badge className={classes.badge}>PHP</Badge>
    </Box>
  );
};

export default Category;
