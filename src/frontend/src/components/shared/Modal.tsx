import { Modal, useMantineTheme } from "@mantine/core";
import { ReactNode } from "react";

interface Props {
  opened: boolean;
  close: () => void;
  children: ReactNode;
  title: string;
}

function RegisterModal({ opened, close, children, title }: Props) {
  const theme = useMantineTheme();

  return (
    <>
      <Modal
        opened={opened}
        onClose={close}
        title={title}
        overlayProps={{
          color:
            theme.colorScheme === "dark"
              ? theme.colors.dark[9]
              : theme.colors.gray[2],
          opacity: 0.55,

          blur: 3,
        }}
        xOffset={-30}
        centered
      >
        {children}
      </Modal>
    </>
  );
}

export default RegisterModal;
