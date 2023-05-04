import { Button, createStyles, rem } from "@mantine/core";

const useStyles = createStyles((theme) => ({
  skelotonBadge: {
    marginTop: rem(5),
    borderRadius: 0,
    marginRight: rem(30),
    width: rem(93),
    height: rem(30),
    backgroundColor: theme.colors.gray[1],
  },
}));

function CategorySkeleton() {
  const { classes } = useStyles();
  return <Button className={classes.skelotonBadge} variant="light" />;
}

export default CategorySkeleton;
