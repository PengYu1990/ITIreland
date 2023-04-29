import { Badge, Box, createStyles, rem } from "@mantine/core";

const useStyles = createStyles((theme) => ({
  category: {
    marginTop: rem(10),
  },
  badge: {
    padding: rem(15),
    borderRadius: 0,
    fontSize: rem(14),
    marginRight: rem(30),
  },
}));

const Category = () => {
  const { classes } = useStyles();
  return (
    <Box className={classes.category}>
      <Badge
        className={classes.badge}
        variant="gradient"
        gradient={{ from: "indigo", to: "cyan" }}
      >
        Indigo cyan
      </Badge>
      <Badge className={classes.badge}>Indigo cyan</Badge>
      <Badge className={classes.badge}>Indigo cyan</Badge>
    </Box>
  );
};

export default Category;
