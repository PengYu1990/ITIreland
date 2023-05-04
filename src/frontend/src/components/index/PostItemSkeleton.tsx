import { Skeleton } from "@mantine/core";

function PostItemSkeleton() {
  return (
    <>
      <Skeleton height={30} mt={20} width="70%" />
      <Skeleton height={12} mt={15} />
      <Skeleton height={12} mt={6} />
      <Skeleton height={12} mt={6} />
      <Skeleton height={12} mt={6} />
    </>
  );
}

export default PostItemSkeleton;
