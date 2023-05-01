import { IconArrowUp } from "@tabler/icons-react";
import { useWindowScroll } from "@mantine/hooks";
import { Affix, Button, MediaQuery, Transition, rem } from "@mantine/core";

export default function ToTop() {
  const [scroll, scrollTo] = useWindowScroll();

  return (
    <MediaQuery smallerThan="md" styles={{ display: "none" }}>
      <Affix position={{ bottom: rem(105), right: rem(70) }}>
        <Transition transition="slide-up" mounted={scroll.y > 0}>
          {(transitionStyles) => (
            <Button
              leftIcon={<IconArrowUp size="1rem" />}
              style={transitionStyles}
              onClick={() => scrollTo({ y: 0 })}
            >
              Scroll to top
            </Button>
          )}
        </Transition>
      </Affix>
    </MediaQuery>
  );
}
