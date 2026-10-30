# Nereis U5.3 — original PNG intake

Download the prepared file `nereis_u53_eight_originals.zip` from the ChatGPT conversation.
ZIP bytes: 16,974,812
ZIP SHA-256: `f552a6156fc09343700d2a12e534b2c5b17e499924b1c09f3b954c48c5325250`

Open this `qa-intake` directory on the `native/u53-nereis-eight-static-png-masters-20261017` branch and select **Add file → Upload files**. Upload the single ZIP here without extracting it on the GitHub website.

The import workflow will verify all eight embedded PNG files and add them to `native-app/app/src/main/assets/nereis/` using the existing exact SHA-256 manifest. The temporary ZIP is then removed.

After the binary import succeeds, run the separate Unit IV native Android validation workflow on the new branch HEAD and inspect its screenshots. Academic anatomy and Tamil terminology approval remain PENDING.
